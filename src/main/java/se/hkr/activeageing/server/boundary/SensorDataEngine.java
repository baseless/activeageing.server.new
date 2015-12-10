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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Optional<Sensordata> getSensorData(int sensorDataId) {
        Optional<Sensordata> result = Optional.empty();
        try {
            Sensordata sensorData = super.find(sensorDataId);
            result = Optional.of(sensorData);
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public Optional<Collection<SensordataReportvalues>> allEvents(int sensorDataId) {
        Optional<Collection<SensordataReportvalues>> result = Optional.empty();
        try {
            Sensordata data = super.find(sensorDataId);
            result = Optional.of(data.getSensordataReportvaluesCollection());
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public int insert(SensorDataViewModel viewModel) {
        int result = 0;
        try {
            Sensordata sensorData = new Sensordata();

            sensorData.setAlarmDefinition(viewModel.getAlarmDefinition());
            sensorData.setAlarmState(viewModel.getAlarmState());
            sensorData.setAlarmTimeActive(viewModel.getAlarmTimeActive());
            sensorData.setBatteryState(viewModel.getBatteryState());
            sensorData.setRepeater(viewModel.getRepeater());
            sensorData.setBatteryVoltage(viewModel.getBatteryVoltage());
            sensorData.setCreated(stringToTimeStamp(viewModel.getCreated()));
            sensorData.setGatewayTimestamp(stringToTimeStamp(viewModel.getGatewayTimestamp()));
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
            sensorData.setReportTime(stringToTimeStamp(viewModel.getReportTime()));
            sensorData.setTimeSinceConnection(viewModel.getTimeSinceConnection());
            sensorData.setTimeSinceConnectionState(viewModel.getTimeSinceConnectionState());
            sensorData.setVersion(viewModel.getVersion());
            em.persist(sensorData);
            em.flush();

            for(SensorReportValue reportModel : viewModel.getReportValues()) {
                SensordataReportvalues report = new SensordataReportvalues();
                if(reportModel.getDataUnitType() != null) {
                    report.setDataUnitTypehref(reportModel.getDataUnitType().getHref());
                    report.setDataUnitTypeuuid(reportModel.getDataUnitType().getUuid());
                }
                report.setValue(reportModel.getValue());
                report.setGatewayTimestamp(stringToTimeStamp(reportModel.getGatewayTimestamp()));
                if(reportModel.getSensor() != null) {
                    report.setSensorHref(reportModel.getSensor().getHref());
                    report.setSensorUuid(reportModel.getSensor().getUuid());
                }
                report.setSensordataId(sensorData);
                em.persist(report);
            }

            result = sensorData.getId();

        } catch(Exception e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);

        }
        return result;
    }

    private Timestamp stringToTimeStamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        long timeInLong = 0;
        try {
            timeInLong = sdf.parse(time).getTime();
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
        return new Timestamp(timeInLong);
    }

    public Optional<Collection<Sensordata>> sortByDate(String date){

        Timestamp tm = Timestamp.valueOf(date);
        Optional<Collection<Sensordata>> result = Optional.empty();
        try {
            List<Sensordata> sensordata = em.createNamedQuery("Sensordata.sortByDate", Sensordata.class)
                    .setParameter("date", tm)
                    .getResultList();
            result = Optional.of(sensordata);
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public Optional<Collection<Sensordata>> sortByDate(String fromDate, String toDate){

        Timestamp fromDateTm = Timestamp.valueOf(fromDate);
        Timestamp toDateTm = Timestamp.valueOf(toDate);
        Optional<Collection<Sensordata>> result = Optional.empty();
        try {
            List<Sensordata> sensordata = em.createNamedQuery("Sensordata.sortByFromDateToDate", Sensordata.class)
                    .setParameter("fromdate", fromDateTm)
                    .setParameter("todate", toDateTm)
                    .getResultList();
            result = Optional.of(sensordata);
        } catch(Exception e) {
            logger.warn(e.getMessage());
        }
        return result;
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
