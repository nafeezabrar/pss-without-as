package pss.mapping.ooi_user_group;

import pss.data.dimension.Dimension;
import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.ooi_id.MultiLocalOoiMapper;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.MultiOoiCombination;
import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.local.combination.MultiLocalOoiCombination;
import pss.data.pssvariable.group.MultiPssGroup;
import pss.data.pssvariable.group.PssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapper.user.UserMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleMultiOoiUserGroupMapper implements MultiOoiUserGroupMappable {

    protected final Set<PssGroup> pssGroups;
    protected final UserMapper userMapper;
    protected final OoiGroupMapper ooiGroupMapper;
    protected final UserGroupMapper userGroupMapper;

    public SimpleMultiOoiUserGroupMapper(Set<PssGroup> pssGroups, UserMapper userMapper, OoiGroupMapper ooiGroupMapper, UserGroupMapper userGroupMapper) {
        this.pssGroups = pssGroups;
        this.userMapper = userMapper;
        this.ooiGroupMapper = ooiGroupMapper;
        this.userGroupMapper = userGroupMapper;
    }


    @Override
    public MultiOoiCombination getOoiCombination(MultiLocalOoiCombination localOoiCombination, User user) throws PssException {
        MultiPssGroup pssGroup = (MultiPssGroup) userGroupMapper.getPssGroup(user);
        return getOoiCombination(localOoiCombination, pssGroup);
    }

    @Override
    public MultiLocalOoiCombination getLocalOoiCombination(MultiOoiCombination ooiCombination) throws PssException {
        MultiPssGroup pssGroup = getPssGroup(ooiCombination);
        MultiLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Dimension, Ooi> ooiMap = ooiCombination.getOoiMap();
        Map<Dimension, Map<Ooi, Integer>> ooiToIdMaps = localOoiMapper.getOoiToIdMaps();
        Map<Dimension, Integer> localOoiIdMap = new HashMap<>();
        for (Dimension dimension : ooiToIdMaps.keySet()) {
            Ooi ooi = ooiMap.get(dimension);
            localOoiIdMap.put(dimension, ooiToIdMaps.get(dimension).get(ooi));
        }
        return new MultiLocalOoiCombination(localOoiIdMap);
    }

    @Override
    public MultiOoiCombination getOoiCombination(MultiLocalOoiCombination localOoiCombination, MultiPssGroup pssGroup) {
        MultiLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Dimension, Map<Integer, Ooi>> idToOoiMaps = localOoiMapper.getIdToOoiMaps();
        Map<Dimension, Integer> localIdMap = localOoiCombination.getLocalOoiMap();
        Map<Dimension, Ooi> ooiMap = new HashMap<>();
        for (Dimension dimension : idToOoiMaps.keySet()) {
            Map<Integer, Ooi> idToOoiMap = idToOoiMaps.get(dimension);
            int localOoiId = localIdMap.get(dimension);
            Ooi ooi = idToOoiMap.get(localOoiId);
            ooiMap.put(dimension, ooi);
        }
        return new MultiOoiCombination(ooiMap);
    }

    @Override
    public MultiLocalOoiCombination getLocalOoiCombination(MultiOoiCombination ooiCombination, MultiPssGroup pssGroup) {
        Map<Dimension, Ooi> ooiMap = ooiCombination.getOoiMap();
        MultiLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Dimension, Integer> localOoiMap = new HashMap<>();
        Map<Dimension, Map<Ooi, Integer>> ooiToIdMaps = localOoiMapper.getOoiToIdMaps();
        for (Dimension dimension : ooiToIdMaps.keySet()) {
            Map<Ooi, Integer> ooiToLocalIdMap = ooiToIdMaps.get(dimension);
            Ooi ooi = ooiMap.get(dimension);
            int localOoiId = ooiToLocalIdMap.get(ooi);
            localOoiMap.put(dimension, localOoiId);
        }
        return new MultiLocalOoiCombination(localOoiMap);
    }

    @Override
    public MultiPssGroup getPssGroup(User user) throws PssException {
        return (MultiPssGroup) userGroupMapper.getPssGroup(user);
    }

    @Override
    public MultiPssGroup getPssGroup(int userId) throws PssException {
        User user = userMapper.getUser(userId);
        return getPssGroup(user);
    }

    @Override
    public MultiPssGroup getPssGroup(MultiOoiCombination ooiCombination) throws PssException {
        Map<OoiCombination, PssGroup> ooiCombinationToPssGroupMap = ooiGroupMapper.getOoiCombinationToPssGroupMap();
        if (ooiCombinationToPssGroupMap.containsKey(ooiCombination)) {
            return (MultiPssGroup) ooiCombinationToPssGroupMap.get(ooiCombination);
        }
        throw new PssException(String.format("Ooi combination %s not included in any pss group", ooiCombination.toString()));
    }

    @Override
    public Set<User> getUsers(MultiPssGroup pssGroup) throws PssException {
        Map<PssGroup, Set<User>> userSetMap = userGroupMapper.getUserSetMap();
        if (userSetMap.containsKey(pssGroup)) {
            return userSetMap.get(pssGroup);
        }
        throw new PssException(String.format("pss group with id %d not recognized or not mapped with any user", pssGroup.getPssGroupId()));

    }

    @Override
    public User getUser(int userId) throws PssException {
        return userMapper.getUser(userId);
    }

    @Override
    public Ooi getOoi(int localOoiId, User user) throws PssException {
        throw new PssException("get ooi cannot be called without dimension for multi pss");
    }

    @Override
    public Ooi getOoi(int localOoiId, int userId) throws PssException {
        throw new PssException("get ooi cannot be called without dimension for multi pss");
    }

    @Override
    public Ooi getOoi(Dimension dimension, int localOoiId, User user) throws PssException {
        MultiPssGroup pssGroup = getPssGroup(user);
        MultiLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Dimension, Map<Integer, Ooi>> idToOoiMaps = localOoiMapper.getIdToOoiMaps();
        if (idToOoiMaps.containsKey(dimension)) {
            Map<Integer, Ooi> idToOoiMap = idToOoiMaps.get(dimension);
            if (idToOoiMap.containsKey(localOoiId)) {
                return idToOoiMap.get(localOoiId);
            }
            throw new PssException(String.format("Ooi with local Ooi id %d for user with id %d not found in his pss group", localOoiId, user.getId()));
        }
        throw new PssException(String.format("dimension %s not recognized", dimension.toString()));
    }

    @Override
    public Ooi getOoi(Dimension dimension, int localOoiId, int userId) throws PssException {
        User user = getUser(userId);
        return getOoi(dimension, localOoiId, user);
    }
}
