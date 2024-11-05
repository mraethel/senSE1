{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";

    mraethel.url = "github:mraethel/mraethel.nix";

    systems.url = "github:nix-systems/x86_64-linux";
    flakeUtils.url = "github:numtide/flake-utils";
    flakeUtils.inputs.systems.follows = "systems";
  };

  outputs =
  { self
  , nixpkgs
  , mraethel
  , flakeUtils
  , ...
  }: rec {
    nixvimModules.config.default.plugins = {
      lsp.servers.jdt-language-server.enable = true;
      plantuml-syntax.enable = true;
    };
    nixvimConfigurations.default = { system }: (mraethel.nixvimConfigurations.basic { inherit system; }).extend nixvimModules.config.default;
  } // flakeUtils.lib.eachDefaultSystem (system: let
    pkgs = import nixpkgs { inherit system; };
  in rec {
    packages = rec {
      nvim = self.nixvimConfigurations.default { inherit system; };
      plantuml = pkgs.writeScriptBin "put" "${ pkgs.plantuml }/bin/plantuml -tpdf $@";
      junit = let
        version = "1.11.2";
      in pkgs.fetchurl {
        url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/${ version }/junit-platform-console-standalone-${ version }.jar";
        sha256 = "u6I05YgLEER4fUu+uH4uTA06hf3Gn02BoSrrz99tVBM=";
      };
      jtest = pkgs.writeScriptBin "junit" ''
        if [ -d $CLASSPATH ]; then
          java -jar ${ junit } execute --scan-classpath -cp $CLASSPATH
        else
          echo "Failure: CLASSPATH is $CLASSPATH!"
        fi
      '';
      picocli = let
        version = "4.7.6";
      in pkgs.fetchurl {
        url = "https://repo1.maven.org/maven2/info/picocli/picocli/${ version }/picocli-${ version }.jar";
        sha256 = "7UQRg/MJuT8QTKngceMUpAYqiTGE4Yo8etcuycuhK6A=";
      };
      jline = let
        version = "3.27.1";
      in pkgs.fetchurl {
        url = "https://repo1.maven.org/maven2/org/jline/jline/${ version }/jline-${ version }.jar";
        sha256 = "cvy8WNoFCSBnc53tYu1rG6kHXs0P7hyqY0wsvxoW/no=";
      };
      jcmd = pkgs.writeScriptBin "jcmd" ''
        if [ -d $CLASSPATH ]; then
          java -cp $CLASSPATH:${ picocli }:${ jline } $@
        else
          echo "Failure: CLASSPATH is $CLASSPATH!"
        fi
      '';
      jcc = pkgs.writeScriptBin "jcc" ''
        shopt -s globstar
        if [ -d $GIT_ROOT ]; then
          javac -d $CLASSPATH -cp "${ junit }:${ picocli }:${ jline }" $GIT_ROOT/src/**/*.java $GIT_ROOT/test/**/*.java
        else
          echo "Failure: GIT_ROOT is $GIT_ROOT!"
        fi
      '';
    };
    devShells.default = pkgs.mkShell {
      name = "senSE1";
      packages = (with packages; [
        nvim
        plantuml
        jtest
        jcmd
        jcc
      ]) ++ (with pkgs; [
        git
        openjdk
      ]);
      shellHook = ''
        GIT_ROOT=$(git rev-parse --show-toplevel) \
        CLASSPATH=$GIT_ROOT/out \
        EDITOR=nvim \
        exec $SHELL
      '';
    };
  });
}
