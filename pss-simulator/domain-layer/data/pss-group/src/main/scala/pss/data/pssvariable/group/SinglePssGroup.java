package pss.data.pssvariable.group;

import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.ooi.collection.SingleOoiCollection;
import pss.data.ooi.local.collection.SingleLocalOoiCollection;

public class SinglePssGroup extends PssGroup<SingleOoiCollection, SingleLocalOoiCollection, SingleLocalOoiMapper> {

    public SinglePssGroup(SingleOoiCollection ooiCollection, int pssGroupId, SingleLocalOoiCollection localOoiCollection, SingleLocalOoiMapper localOoiMapper) {
        super(ooiCollection, pssGroupId, localOoiCollection, localOoiMapper);
    }
}
