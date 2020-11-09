package pss.reader.pss_group;

import pss.data.pssvariable.group.PssGroup;

import java.util.Set;

public interface PssGroupingReadable {
    Set<PssGroup> readPssGrouping();
}
