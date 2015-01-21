/**
 * @author Boban Jevtic
 * Version: 1.0
 * Task: Distributed PI Calculator
 */
package compute;

public interface Task<T> {
    T execute();
}