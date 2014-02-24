package org.openmrs.modulus.models

/**
 * Provides domain object property to check completedness&emdash;whether the properties of the object have all been
 * filled in and the object is ready to be used normally.
 * Created by herooftime on 2/20/14.
 */
public interface Completable {

    Boolean complete

    /**
     * Check other properties of the object to determine whether it is completed
     * @return true or false as to whether the object is completed
     */
    boolean completed()

}