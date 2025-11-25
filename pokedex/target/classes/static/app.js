document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("searchForm");
    const input = document.getElementById("pokemonName");
    const resultDiv = document.getElementById("result");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const name = input.value.trim().toLowerCase();
        if (!name) {
            resultDiv.innerHTML = "<p>Please enter a Pokémon name.</p>";
            return;
        }

        resultDiv.innerHTML = "<p>Loading...</p>";

        fetch(`http://localhost:8081/api/v1/pokemon/${name}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Pokémon not found");
                }
                return response.json();
            })
            .then(data => {
				
				
				//fixed json mapping
				const types = data.types.map(t => t.type.name);
				                const abilities = data.abilities.map(a => a.ability.name);
				                const stats = data.stats.map(s => ({
				                    name: s.stat.name,
				                    value: s.base_stat
				                }));
								
								
                resultDiv.innerHTML = `
                    <h2>${data.name.toUpperCase()}</h2>
					
                    <img src="${data.sprites.front_default}" alt="${data.name}" style="width:150px;height:150px;">
					
                    <p><strong>Height:</strong> ${data.height}</p>
                    <p><strong>Weight:</strong> ${data.weight}</p>
                    <p><strong>Base Experience:</strong> ${data.base_experience}</p>
                    
					<p><strong>Types:</strong> 
					        ${data.types.map(t => t.type.name).join(", ")}
					    </p>

					    <h3>Abilities</h3>
					    <ul>
					        ${data.abilities.map(a => `<li>${a.ability.name}</li>`).join("")}
					    </ul>

					    <h3>Stats</h3>
					    <ul>
					        ${data.stats.map(s => `<li>${s.stat.name}: ${s.base_stat}</li>`).join("")}
					    </ul>
            })
            .catch(error => {
                resultDiv.innerHTML = `
                    <p style="color:red;">${error.message}</p>
                `;
            });
    });
});
