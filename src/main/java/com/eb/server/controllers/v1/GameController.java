package com.eb.server.controllers.v1;

import com.eb.server.api.v1.model.GameCommandDTO;
import com.eb.server.api.v1.model.RequestGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(GameController.BASE_URL)
public class GameController {
    public static final String BASE_URL = "/api/v1/games";

    private final GameService gameService;

    public GameController(GameService gameService) {

        this.gameService = gameService;
    }

    // TODO: expose games/{id}/game-players/{id} for planPhase

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO getGameDTOById(@PathVariable Long id) {
        return gameService.findGameDTOById(id);
    }

    @PostMapping("/find")
    @ResponseStatus(HttpStatus.CREATED)
    public void find(@RequestBody RequestGameDTO requestGameDTO) {

        gameService.requestNewGame(requestGameDTO);
    }

    @PostMapping("/{id}/command")
    @ResponseStatus(HttpStatus.CREATED)
    public void command(@PathVariable Long id,
                           @RequestBody GameCommandDTO gameCommandDTO) {
        gameService.handleCommand(id, gameCommandDTO);
    }
}
