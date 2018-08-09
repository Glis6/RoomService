package com.glis.memory;

import com.glis.exceptions.InvalidKeyException;
import com.glis.exceptions.KeyTypeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
public class MappedSharedMemoryTest {
    /**
     * The memory that is being tested.
     */
    private MappedSharedMemory<String> memory;

    /**
     * Prepare the memory.
     */
    @Before
    public void before() {
        final Map<String, Object> map = new HashMap<>();
        map.put("validKey", 1);
        memory = new MappedSharedMemory<>(map);
    }

    /*
     * All tests for #getState
     */

    @Test(expected = InvalidKeyException.class)
    public void testGetStateInvalidKey() throws Exception {
        memory.getState("invalidKey", Object.class);
    }

    @Test(expected = ClassCastException.class)
    public void testGetStateWrongType() throws Exception {
        memory.getState("validKey", int.class);
    }

    @Test(expected = KeyTypeException.class)
    public void testGetStateNullKey() throws Exception {
        memory.getState(null, int.class);
    }

    @Test(expected = ClassCastException.class)
    public void testGetStateNullClass() throws Exception {
        memory.getState("validKey", null);
    }

    @Test
    public void testGetStateIfValid() throws Exception {
        final int expectedResult = 1;
        final int result = memory.getState("validKey", Integer.class);
        Assert.assertEquals("Expected " + expectedResult + " but got " + result, result, expectedResult);
    }

    /*
     * All tests for #setState
     */

    @Test(expected = KeyTypeException.class)
    public void testSetStateNullKey() throws Exception {
        memory.setState(null, new Object());
    }

    @Test(expected = Exception.class)
    public void testSetStateNullState() throws Exception {
        memory.setState("key", null);
    }

    @Test
    public void testSetState() throws Exception {
        final int startObject = 7;
        final String key = "key";
        memory.setState(key, startObject);
        final int returnObject = memory.getState(key, Integer.class);
        Assert.assertEquals("Expected: " + startObject + ", received: " + returnObject, startObject, returnObject);
    }

    /*
     * All tests for #delete
     */
    @Test(expected = KeyTypeException.class)
    public void deleteNullKey() throws Exception {
        memory.delete(null);
    }

    @Test(expected = InvalidKeyException.class)
    public void deleteNonExistentKey() throws Exception {
        final String key = "nonexistentkey";
        memory.delete(key);
        memory.getState(key, Object.class);
    }

    @Test(expected = InvalidKeyException.class)
    public void deleteActualKey() throws Exception {
        final String key = "actualkey";
        memory.setState(key, key);
        Assert.assertEquals(key, memory.getState(key, String.class));
        memory.delete(key);
        memory.getState(key, Object.class);
    }
}
