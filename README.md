# Pokémon Search Engine 🐾
A simple Pokémon search engine that allows users to search for Pokémon by name and view interesting details fetched from the https://pokeapi.co/docs/v2.

## Features
**Search for a Pokémon by name**
**Display rich Pokémon details such as:**

    1.Abilities
    2.Types
    3.Stats (HP, Attack, Defense, etc.)
    4.Base experience
    5.Images
 **Fast response using caching for repeated queries.**

# Architecture
The project consists of two main components:

## 1. Web Service API

1.Built using Node.js / Java / Python (choose your implementation).

2.Fetches Pokémon data from PokéAPI and exposes it via RESTful endpoints.

3.Implements caching for improved performance:
       -Handles cache expiry.
       -Limits maximum cache entries.

4.Follows REST API design best practices.

## 2. Front-End UI

1.Built using HTML, CSS, JavaScript/TypeScript, and optionally React.js or similar frameworks.

2.Displays Pokémon data in a user-friendly and visually appealing way.

3.Supports dynamic rendering of attributes, images, and stats.
