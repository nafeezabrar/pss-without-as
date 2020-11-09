package pss.data.pssvariable.group;

import pss.data.mapper.ooi_id.LocalOoiMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.collection.OoiCollection;
import pss.data.ooi.local.collection.LocalOoiCollection;

public abstract class PssGroup<TOoiCollection extends OoiCollection, TLocalOoiCollection extends LocalOoiCollection, TLocalOoiMapper extends LocalOoiMapper> {
    protected final int pssGroupId;
    protected final TOoiCollection ooiCollection;
    protected final TLocalOoiCollection localOoiCollection;
    protected final TLocalOoiMapper localOoiMapper;


    public PssGroup(TOoiCollection ooiCollection, int pssGroupId, TLocalOoiCollection localOoiCollection, TLocalOoiMapper localOoiMapper) {
        this.ooiCollection = ooiCollection;
        this.pssGroupId = pssGroupId;
        this.localOoiCollection = localOoiCollection;
        this.localOoiMapper = localOoiMapper;
    }

    public int getPssGroupId() {
        return pssGroupId;
    }

    public TOoiCollection getOoiCollection() {
        return ooiCollection;
    }

    public boolean containsOoi(Ooi ooi) {
        return ooiCollection.containsOoi(ooi);
    }

    public TLocalOoiCollection getLocalOoiCollection() {
        return localOoiCollection;
    }

    public TLocalOoiMapper getLocalOoiMapper() {
        return localOoiMapper;
    }
}
