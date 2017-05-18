package ru.spbau.mit;

/**
 * Created by ivan on 18.05.17.
 */

import org.junit.Rule;
import org.junit.Test;

public class ImplementorImplTest {
    @Test
    public void testimplementFromStandardLibrary() {
        ImplementorImpl im = new ImplementorImpl("src/main/java");
        try {
            im.implementFromStandardLibrary("java.util.Collection");
            im.implementFromStandardLibrary("java.util.AbstractList");
            im.implementFromStandardLibrary("java.util.Map");
        } catch (ImplementorException e) {
            System.out.println(e);
        }
    }

    @Test
    public void implementFromDirectory() {
        ImplementorImpl im = new ImplementorImpl("src/main/java");
        try {
            im.implementFromDirectory("build/classes/main/", "ru.spbau.mit.Sample");
        } catch (ImplementorException e) {
            System.out.println(e);
        }
    }
}

