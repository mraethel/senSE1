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
    packages = {
      nvim = self.nixvimConfigurations.default { inherit system; };
      plantuml = pkgs.writeScriptBin "put" "${ pkgs.plantuml }/bin/plantuml -tpdf $@";
    };
    devShells.default = pkgs.mkShell {
      name = "senSE1";
      packages = (with packages; [
        nvim
        plantuml
      ]);
      shellHook = "exec $SHELL";
    };
  });
}
