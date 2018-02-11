package com.eb.server.services.phases.payloads;

import lombok.Data;

@Data
public class PhasePlanPayload {
    Long planTurnGamePlayerId = 0L;
    Long playedCardId = 0L;
    Boolean skipPlanTurn = false;
}
