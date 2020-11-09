package pss.data.pssvariable.group;

import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.ooi.collection.MultiOoiCollection;
import pss.data.ooi.local.collection.MultiLocalOoiCollection;

public class MultiPssGroup extends PssGroup<MultiOoiCollection, MultiLocalOoiCollection, MultiLocalOoiMapper> {

    public MultiPssGroup(MultiOoiCollection ooiCollection, int pssGroupId, MultiLocalOoiCollection ooiIdCollection, MultiLocalOoiMapper localOoiMapper) {
        super(ooiCollection, pssGroupId, ooiIdCollection, localOoiMapper);
    }
}
