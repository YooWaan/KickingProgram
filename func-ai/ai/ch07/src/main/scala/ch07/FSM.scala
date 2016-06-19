package ch07



class FSM(npc: NPC, app: GameApp) {
  val MOVE    = 1
  val SEARCH  = 2
  val CHASE   = 3
  val ATTACK  = 4
  val ESCAPE  = 5
  val SLEEP   = 6
  val stateLabel = Array("", "Move", "Search", "Chase", "Attack", "Escape", "Sleep")

  var state        = MOVE
  var energy       = 50.0
  var intelligence = 8.0
  var touched      = false

  val fullEnergy   = 100
  val fullIntelligence = 10


  def action() {
    state match {
      case MOVE =>
        if (touched) state = ATTACK
        else if (energy < fullEnergy) energy += 0.1
        else if (energy >= fullEnergy) state = SEARCH
      case SEARCH =>
        energy -= 0.05
        if (energy <= 0) state = MOVE
        else if (touched) state = ATTACK
        else if (intelligence >= fullIntelligence) state = CHASE

      case CHASE =>
        energy -= 0.1
        if (energy <= 0) state = MOVE
        else if (touched) state = ATTACK

      case ATTACK =>
        energy = math.max(0, energy - 10)
        intelligence += 1
        state = ESCAPE

      case ESCAPE =>
        energy -= 0.1
        if (energy <= 0) state = SLEEP

      case SLEEP =>
        if (energy < fullEnergy) energy += 0.1
        if (energy <= fullEnergy/2) state = MOVE
    }
  }


}
