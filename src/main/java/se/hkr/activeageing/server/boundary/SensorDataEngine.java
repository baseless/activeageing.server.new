package se.hkr.activeageing.server.boundary;

import org.slf4j.Logger;
import se.hkr.activeageing.server.core.qualifiers.DefaultLogger;
import se.hkr.activeageing.server.entities.Accounts;
import se.hkr.activeageing.server.entities.Sensordata;
import se.hkr.activeageing.server.entities.SensordataReportvalues;
import se.hkr.activeageing.server.viewmodels.SensorDataViewModel;
import se.hkr.activeageing.server.viewmodels.SensorReportValue;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by base on 2015-12-08.
 */
@Stateless
public class SensorDataEngine extends AbstractEngine<Sensordata>{

    @PersistenceContext(unitName = "DefaultJtaUnit")
    private EntityManager em;

    @Inject
    @DefaultLogger
    private Logger logger;

    public SensorDataEngine() { super(Sensordata.class); }

    public boolean insert(SensorDataViewModel viewModel) {
        boolean result = false;
        try {
            Sensordata sensorData = new Sensordata();

            sensorData.setAlarmDefinition(viewModel.getAlarmDefinition());
            sensorData.setAlarmState(viewModel.getAlarmState());
            sensorData.setAlarmTimeActive(viewModel.getAlarmTimeActive());
            sensorData.setBatteryState(viewModel.getBatteryState());

            sensorData.setBatteryVoltage(viewModel.getBatteryVoltage());
            Timestamp current = new Timestamp((new Date()).getTime());
            sensorData.setCreated(current);
            //sensorData.setGatewayTimestamp(viewModel.getGatewayTimestamp());
            if(viewModel.getSender() != null) {
                sensorData.setSenderHref(viewModel.getSender().getHref());
                sensorData.setSenderUuid(viewModel.getSender().getUuid());
            }
            if(viewModel.getReportEventType() != null) {
                sensorData.setReportEventTypehref(viewModel.getReportEventType().getHref());
                sensorData.setReportEventTypeid(viewModel.getReportEventType().getId());
            }
            sensorData.setMissed(viewModel.getMissed());
            sensorData.setMissedAvg(viewModel.getMissedAvg());
            sensorData.setMissedState(viewModel.getMissedState());
            sensorData.setNodeEventTimeOut(viewModel.getNodeEventTimeOut());
            //sensorData.setReportTime(viewModel.getReportTime());
            sensorData.setTimeSinceConnection(viewModel.getTimeSinceConnection());
            sensorData.setTimeSinceConnectionState(viewModel.getTimeSinceConnectionState());
            sensorData.setVersion(viewModel.getVersion());
            em.persist(sensorData);
            em.flush();

            for(SensorReportValue reportModel : viewModel.getReportValues()) {
                SensordataReportvalues report = new SensordataReportvalues();
                if(reportModel.getDataUnitType() != null)
                report.setDataUnitTypehref(reportModel.getDataUnitType().getHref());
                report.setDataUnitTypeuuid(reportModel.getDataUnitType().getUuid());
                //report.setGatewayTimestamp(reportModel.getGatewayTimestamp());
                if(reportModel.getSensor() != null) {
                    report.setSensorHref(reportModel.getSensor().getHref());
                    report.setSensorUuid(reportModel.getSensor().getUuid());
                }
                report.setSensordataId(sensorData);
                em.persist(report);
            }

            result = true;

        } catch(Exception e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);

        }
        return result;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
