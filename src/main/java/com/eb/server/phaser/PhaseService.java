package com.eb.server.phaser;

public interface PhaseService {
    // define next phase
        // each phase will have it's own implementation interface
        // a phase might have it's own DTO for the payload, like example resolution have stat information
        // execute bot actions
    // execute a command phase
        // each command will have it's own class
        // a command will have different request DTO, DRAW vs PLAY(x)
    // return always a decorated game?
}
