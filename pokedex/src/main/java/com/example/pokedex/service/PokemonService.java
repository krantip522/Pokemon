package com.example.pokedex.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
public class PokemonService {
	private final RestTemplate restTemplate;
	private final String pokeApiBase;


	// simple JSON cache: key=name (lowercase), value=Map (JSON tree converted to Map)
	private final Cache<String, Map> cache;


	public PokemonService(RestTemplate restTemplate,
	@Value("${pokeapi.base:https://pokeapi.co/api/v2}") String pokeApiBase,
	@Value("${cache.maxEntries:500}") int maxEntries,
	@Value("${cache.expireMinutes:10}") int expireMinutes) {
	this.restTemplate = restTemplate;
	this.pokeApiBase = pokeApiBase;
	this.cache = Caffeine.newBuilder()
	.maximumSize(maxEntries)
	.expireAfterWrite(Duration.ofMinutes(expireMinutes))
	.build();
	}


	/**
	* Fetch pokemon details from cache or vendor (PokeAPI). Returns Optional.empty() if not found.
	*/
	public Optional<Map> getPokemon(String nameOrId) {
	String key = nameOrId.trim().toLowerCase();


	// try cache first
	Map cached = cache.getIfPresent(key);
	if (cached != null) {
	return Optional.of(cached);
	}


	String url = String.format("%s/pokemon/%s", pokeApiBase, key);
	try {
	ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
	if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
	Map body = resp.getBody();
	// store in cache
	cache.put(key, body);
	return Optional.of(body);
	}
	} catch (HttpClientErrorException.NotFound nf) {
	return Optional.empty();
	} catch (Exception ex) {
	// log (omitted for brevity) - return Optional.empty to indicate failure
	return Optional.empty();
	}
	return Optional.empty();
	}
}
