package pl.edu.agh.to2.example.models

import pl.edu.agh.to2.models.Call
import pl.edu.agh.to2.models.CallStack
import spock.lang.Specification

class CallStackTest extends Specification {

    def "Should get proper Calls in reversed order"() {
        given:
        def callStack = new CallStack()
        def callToAdd1 = new Call("call1")
        def callToAdd2 = new Call("call2")

        when:
        callStack.addCall(callToAdd1)
        callStack.addCall(callToAdd2)

        then:
        callStack.getPrev() == callToAdd2
        callStack.getPrev() == callToAdd1
    }

    def "Should get proper Calls in order"() {
        given:
        def callStack = new CallStack()
        def callToAdd1 = new Call("call1")
        def callToAdd2 = new Call("call2")

        when:
        callStack.addCall(callToAdd1)
        callStack.addCall(callToAdd2)
        callStack.getPrev()
        callStack.getPrev()

        then:
        callStack.getNext() == callToAdd1
        callStack.getNext() == callToAdd2
    }
}
