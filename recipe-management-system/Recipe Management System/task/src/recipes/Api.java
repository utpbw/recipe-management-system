package recipes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class Api {
    private final Map<Long, Recipe> recipes = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1); // Generates unique IDs

    @PostMapping(value = "/recipe/new", consumes = "application/json")
    public ResponseEntity<Map<String, Long>> newRecipe(@RequestBody Recipe recipe) {
        long id = idCounter.getAndIncrement(); // Generate unique ID
        recipe.setId(id); // Assign ID to recipe
        recipes.put(id, recipe); // Store recipe with ID
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Recipe recipe = recipes.get(id);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }
}

