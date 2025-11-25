package com.example.pokedex.controller;
import com.example.pokedex.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    /**
     * Fetch pokemon by name or id.
     * Example: GET /api/v1/pokemon/by-name/pikachu
     */
    @GetMapping("/{name}")
    public ResponseEntity<?> getPokemon(@PathVariable("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "name must be provided"));
        }

        return pokemonService.getPokemon(name)
                .map(data -> ResponseEntity.ok().body(data))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "pokemon not found")));
    }

    /**
     * Search using query param.
     * Example: GET /api/v1/pokemon/search?q=pikachu
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("q") String q) {
        return getPokemon(q);
    }
}
