package pl.edu.agh.to2.example.models

import pl.edu.agh.to2.models.Turtle
import spock.lang.Specification

class CoordChangeTest extends Specification {

    def "Check if function sets turtle's position properly #1"() {
        given:
        def turtle = new Turtle(50, 100, false, 0)

        when:
        def coordChange = turtle.move(100, 0)

        then:
        coordChange.getX() == 150
        coordChange.getY() == 100
        coordChange.getOldX() == 50
        coordChange.getOldY() == 100
    }

    def "Check if function sets turtle's position properly #2"() {
        given:
        def turtle = new Turtle(50, 100, false, 0)

        when:
        def coordChange = turtle.move(0, 0)

        then:
        coordChange.getX() == 50
        coordChange.getY() == 100
        coordChange.getOldX() == 50
        coordChange.getOldY() == 100
    }
}
