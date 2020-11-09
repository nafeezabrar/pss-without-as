package pss.factory.report.generation.deanonymizer;

import pss.config.task.DeanonymizationConfig.DeanonymizationMethod;
import pss.data.oc_table.MultiOcTable;
import pss.data.oc_table.SingleOcTable;
import pss.data.pss_type.PssType;
import pss.deanonymization.Deanonymizable;
import pss.deanonymization.idgas.MultiIdgasDeanonymizer;
import pss.deanonymization.idgas.MultiIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.SingleIdgasDeanonymizer;
import pss.deanonymization.idgas.SingleIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.octable.updating.SimpleMultiIdgasIdgasDecodingTableUpdater;
import pss.deanonymization.idgas.octable.updating.SimpleSingleIdgasIdgasDecodingTableUpdater;
import pss.decodability.checking.IdealMultiIdgasDecodabilityChecker;
import pss.decodability.checking.IdealSingleIdgasDecodabilityChecker;
import pss.decodability.checking.MultiDecodabilityChecker;
import pss.decodability.checking.SingleDecodabilityChecker;
import pss.exception.PssException;

import static java.lang.String.format;

public class DeanonymizerFactory {
    public static Deanonymizable createDeanonymizer(PssType pssType, DeanonymizationMethod deanonymizationMethod) throws PssException {
        switch (deanonymizationMethod) {
            case IDGAS:
                switch (pssType.getPssDimensionType()) {
                    case SINGLE:
                        SingleOcTable singleOcTable = new SingleOcTable();
                        SingleIdgasDecodingTableUpdater ocTableUpdater = new SimpleSingleIdgasIdgasDecodingTableUpdater(singleOcTable);
                        SingleDecodabilityChecker decodabilityChecker = new IdealSingleIdgasDecodabilityChecker(singleOcTable);
                        return new SingleIdgasDeanonymizer(singleOcTable, decodabilityChecker, ocTableUpdater);
                    case MULTI:
                        MultiOcTable multiOcTable = new MultiOcTable(pssType.getDimensionSet().size(), pssType.getDimensionSet());
                        MultiIdgasDecodingTableUpdater multiOcTableUpdater = new SimpleMultiIdgasIdgasDecodingTableUpdater(multiOcTable);
                        MultiDecodabilityChecker multiDecodabilityChecker = new IdealMultiIdgasDecodabilityChecker(multiOcTable);
                        return new MultiIdgasDeanonymizer(multiOcTable, multiDecodabilityChecker, multiOcTableUpdater);
                }
        }
        throw new PssException(format("Deanonymizer type %s not recognized to factory", deanonymizationMethod.toString()));
    }
}
