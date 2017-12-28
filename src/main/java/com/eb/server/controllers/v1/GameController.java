package com.eb.server.controllers.v1;

import com.eb.server.api.v1.model.FindGameDTO;
import com.eb.server.api.v1.model.GameDTO;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(GameController.BASE_URL)
public class GameController {
    public static final String BASE_URL = "/api/v1/games";

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/find")
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO findGame(@RequestBody FindGameDTO findGameDTO) {
        return gameService.createNewGame(findGameDTO);
    }
}
