package pl.edu.agh.to2.example.models

import pl.edu.agh.to2.models.Turtle
import spock.lang.Shared
import spock.lang.Specification
import static spock.util.matcher.HamcrestMatchers.closeTo

class TurtleTest extends Specification {

    def static distance = 100
    def static angle = 60
    def static triangleAngle = 120

    def eps = 1e-6

    @Shared turtle = new Turtle(0, 0, false, 0)

    def cleanup() {
        turtle = new Turtle(0, 0, false, 0)
    }

    def "Should return proper Equilateral triangle vertex values [Check x]"() {
        expect:
        new Double(givenTurtle.move(givenDistance, givenAngle).getX()) closeTo(expectedX,eps)

        where:
        givenTurtle | givenDistance | givenAngle    || expectedX
        turtle      | distance      | triangleAngle || 100
        turtle      | distance      | triangleAngle || 50
        turtle      | distance      | triangleAngle || 0
    }

    def "Should return proper Equilateral triangle vertex values [Check y]"() {
        expect:
        new Double(givenTurtle.move(givenDistance, givenAngle).getY()) closeTo(expectedY, eps)

        where:
        givenTurtle | givenDistance | givenAngle    || expectedY
        turtle      | distance      | triangleAngle || 0
        turtle      | distance      | triangleAngle || -distance * Math.sqrt(3) / 2
        turtle      | distance      | triangleAngle || 0
    }

    def "Should return proper turtle's position"() {
        given:
        def turtle = new Turtle(0, 0, true, angle)

        when:
        turtle.setPosition(distance, distance, 2 * angle)

        then:
        new Double(turtle.getX()) closeTo(distance, eps)
        new Double(turtle.getY()) closeTo(distance, eps)
        new Double(turtle.getAngle()) closeTo(2 * angle, eps)
    }

    def "Should return positive x when move turtle horizontally"() {
        given:
        def turtle = new Turtle(0, 0, true, 0)

        when:
        turtle.move(distance, 90)

        then:
        new Double(turtle.getX()) closeTo(distance, eps)
        new Double(turtle.getY()) closeTo(0, eps)
        new Double(turtle.getAngle()) closeTo(90, eps)
    }

    def "Should return negative x when move turtle horizontally"() {
        given:
        def turtle = new Turtle(0, 0, true, 0)

        when:
        turtle.move(-distance, 90)

        then:
        new Double(turtle.getX()) closeTo(-distance, eps)
        new Double(turtle.getY()) closeTo(0, eps)
        new Double(turtle.getAngle()) closeTo(90, eps)
    }

    def "Should return positive y when move turtle vertically"() {
        given:
        def turtle = new Turtle(0, 0, true, 270)

        when:
        turtle.move(distance, 0)

        then:
        new Double(turtle.getX()) closeTo(0, eps)
        new Double(turtle.getY()) closeTo(distance, eps)
        new Double(turtle.getAngle()) closeTo(270, eps)
    }

    def "Should negative y return when move turtle vertically"() {
        given:
        def turtle = new Turtle(0, 0, true, 270)

        when:
        turtle.move(-distance, 0)

        then:
        new Double(turtle.getX()) closeTo(0, eps)
        new Double(turtle.getY()) closeTo(-distance, eps)
        new Double(turtle.getAngle()) closeTo(270, eps)
    }

    def "Should return positive x, negative y when move turtle skew"() {
        given:
        def turtle = new Turtle(0, 0, true, 45)

        when:
        turtle.move(distance, 0)

        then:
        new Double(turtle.getX()) closeTo(distance / Math.sqrt(2), eps)
        new Double(turtle.getY()) closeTo(-distance / Math.sqrt(2), eps)
        new Double(turtle.getAngle()) closeTo(45, eps)
    }

    def "Should return negative x, positive y when move turtle skew"() {
        given:
        def turtle = new Turtle(0, 0, true, 45)

        when:
        turtle.move(-distance, 0)

        then:
        new Double(turtle.getX()) closeTo(-distance / Math.sqrt(2), eps)
        new Double(turtle.getY()) closeTo(distance / Math.sqrt(2), eps)
        new Double(turtle.getAngle()) closeTo(45, eps)
    }

    def "Should return positive x,y when move turtle skew"() {
        given:
        def turtle = new Turtle(0, 0, true, -45)

        when:
        turtle.move(distance, 0)

        then:
        new Double(turtle.getX()) closeTo(distance / Math.sqrt(2), eps)
        new Double(turtle.getY()) closeTo(distance / Math.sqrt(2), eps)
        new Double(turtle.getAngle()) closeTo(-45, eps)
    }

    def "Should return negative x,y, positive y when move turtle skew"() {
        given:
        def turtle = new Turtle(0, 0, true, 135)

        when:
        turtle.move(distance, 0)

        then:
        new Double(turtle.getX()) closeTo(-distance / Math.sqrt(2), eps)
        new Double(turtle.getY()) closeTo(-distance / Math.sqrt(2), eps)
        new Double(turtle.getAngle()) closeTo(135, eps)
    }
}
