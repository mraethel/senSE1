{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";

    mraethel.url = "github:mraethel/mraethel.nix";

    nixvim.url = "github:nix-community/nixvim";

    systems.url = "github:nix-systems/x86_64-linux";
    flakeUtils.url = "github:numtide/flake-utils";
    flakeUtils.inputs.systems.follows = "systems";
  };

  outputs = {
    self,
    nixpkgs,
    mraethel,
    nixvim,
    flakeUtils,
    ...
  }: {
    nixVimConfigurations.default = mraethel.nixVimConfigurations.default // {
      plugins = {
        lsp.servers.jdtls.enable = true;
        plantuml-syntax.enable = true;
      };
    };
  } // flakeUtils.lib.eachDefaultSystem (system: let
    pkgs = import nixpkgs { inherit system; };
  in {
    packages = {
      nvim = nixvim.legacyPackages.${ system }.makeNixvim self.nixVimConfigurations.default;
      plantuml = pkgs.writeScriptBin "put" "${ pkgs.plantuml }/bin/plantuml -tpdf $@";
    };
    devShells.default = pkgs.mkShell {
      name = "senSE1";
      packages = (with self.packages.${ system }; [
        nvim
        plantuml
      ]);
      shellHook = "exec $SHELL";
    };
  });
}
