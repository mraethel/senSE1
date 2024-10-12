{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";

    mraethel.url = "github:mraethel/mraethel.nix";

    systems.url = "github:nix-systems/x86_64-linux";
    flakeUtils.url = "github:numtide/flake-utils";
    flakeUtils.inputs.systems.follows = "systems";
  };

  outputs = {
    self,
    nixpkgs,
    mraethel,
    flakeUtils,
    ...
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
        shopt -s globstar
        if [ -d $GIT_ROOT ]; then
          javac -d $GIT_ROOT/out -cp ${ junit } $GIT_ROOT/src/**/*.java $GIT_ROOT/test/**/*.java
          java -jar ${ junit } execute --scan-classpath -cp $GIT_ROOT/out
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
      ]) ++ (with pkgs; [
        git
        openjdk
      ]);
      shellHook = ''
        GIT_ROOT=$(git rev-parse --show-toplevel) \
        EDITOR=nvim \
        exec $SHELL
      '';
    };
  });
}
