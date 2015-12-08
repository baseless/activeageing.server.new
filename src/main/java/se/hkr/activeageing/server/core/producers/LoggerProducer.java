package se.hkr.activeageing.server.core.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Factory used to produce new instances of SLF4J connected Loggers.
 * Created by Daniel Ryhle on 2015-10-24.
 */
public class LoggerProducer {
    @Produces
    @DefaultLogger
    public Logger newInstance(InjectionPoint point) {
        return LoggerFactory.getLogger(point.getMember().getDeclaringClass().getName());
    }
}
