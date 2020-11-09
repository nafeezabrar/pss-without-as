package pss.mapping.ooi_user_group;

import pss.data.mapper.ooi_group.OoiGroupMapper;
import pss.data.mapper.ooi_id.SingleLocalOoiMapper;
import pss.data.mapper.user_group.UserGroupMapper;
import pss.data.ooi.Ooi;
import pss.data.ooi.combination.OoiCombination;
import pss.data.ooi.combination.SingleOoiCombination;
import pss.data.ooi.local.combination.SingleLocalOoiCombination;
import pss.data.pssvariable.group.PssGroup;
import pss.data.pssvariable.group.SinglePssGroup;
import pss.data.user.User;
import pss.exception.PssException;
import pss.mapper.user.UserMapper;

import java.util.Map;
import java.util.Set;

public class SimpleSingleOoiUserGroupMapper implements SingleOoiUserGroupMappable {

    protected final Set<PssGroup> pssGroups;
    protected final UserMapper userMapper;
    protected final OoiGroupMapper ooiGroupMapper;
    protected final UserGroupMapper userGroupMapper;

    public SimpleSingleOoiUserGroupMapper(Set<PssGroup> pssGroups, UserMapper userMapper, OoiGroupMapper ooiGroupMapper, UserGroupMapper userGroupMapper) {
        this.pssGroups = pssGroups;
        this.userMapper = userMapper;
        this.ooiGroupMapper = ooiGroupMapper;
        this.userGroupMapper = userGroupMapper;
    }


    @Override
    public SingleOoiCombination getOoiCombination(SingleLocalOoiCombination localOoiCombination, User user) throws PssException {
        PssGroup pssGroup = userGroupMapper.getPssGroup(user);
        SingleLocalOoiMapper localOoiMapper = (SingleLocalOoiMapper) pssGroup.getLocalOoiMapper();
        Map<Integer, Ooi> idToOoiMap = localOoiMapper.getIdToOoiMap();
        Ooi ooi = idToOoiMap.get(localOoiCombination.getLocalOoi());
        return new SingleOoiCombination(ooi);
    }

    @Override
    public SingleLocalOoiCombination getLocalOoiCombination(SingleOoiCombination ooiCombination) {
        PssGroup pssGroup = ooiGroupMapper.getOoiCombinationToPssGroupMap().get(ooiCombination);
        SingleLocalOoiMapper localOoiMapper = (SingleLocalOoiMapper) pssGroup.getLocalOoiMapper();
        Map<Ooi, Integer> ooiToIdMap = localOoiMapper.getOoiToIdMap();
        Ooi ooi = ooiCombination.getOoi();
        int localOoiId = ooiToIdMap.get(ooi);
        return new SingleLocalOoiCombination(localOoiId);
    }

    @Override
    public SingleOoiCombination getOoiCombination(SingleLocalOoiCombination localOoiCombination, SinglePssGroup pssGroup) {
        SingleLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Integer, Ooi> idToOoiMap = localOoiMapper.getIdToOoiMap();
        Ooi ooi = idToOoiMap.get(localOoiCombination.getLocalOoi());
        return new SingleOoiCombination(ooi);
    }

    @Override
    public SingleLocalOoiCombination getLocalOoiCombination(SingleOoiCombination ooiCombination, SinglePssGroup pssGroup) {
        SingleLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Ooi, Integer> ooiToIdMap = localOoiMapper.getOoiToIdMap();
        Ooi ooi = ooiCombination.getOoi();
        return new SingleLocalOoiCombination(ooiToIdMap.get(ooi));
    }

    @Override
    public SinglePssGroup getPssGroup(User user) throws PssException {
        return (SinglePssGroup) userGroupMapper.getPssGroup(user);
    }

    @Override
    public SinglePssGroup getPssGroup(int userId) throws PssException {
        User user = userMapper.getUser(userId);
        return getPssGroup(user);
    }

    @Override
    public SinglePssGroup getPssGroup(SingleOoiCombination ooiCombination) throws PssException {
        Map<OoiCombination, PssGroup> ooiCombinationToPssGroupMap = ooiGroupMapper.getOoiCombinationToPssGroupMap();
        if (ooiCombinationToPssGroupMap.containsKey(ooiCombination)) {
            return (SinglePssGroup) ooiCombinationToPssGroupMap.get(ooiCombination);
        }
        throw new PssException(String.format("OoiCombination %s not included in any pss group", ooiCombination.toString()));
    }

    @Override
    public Set<User> getUsers(SinglePssGroup pssGroup) throws PssException {
        Map<PssGroup, Set<User>> userSetMap = userGroupMapper.getUserSetMap();
        if (userSetMap.containsKey(pssGroup)) {
            return userSetMap.get(pssGroup);
        }
        throw new PssException(String.format("pssGroup with id %d does not have user set or unrecognized", pssGroup.getPssGroupId()));
    }

    @Override
    public User getUser(int userId) throws PssException {
        return userMapper.getUser(userId);
    }

    @Override
    public Ooi getOoi(int localOoiId, User user) throws PssException {
        SinglePssGroup pssGroup = getPssGroup(user);
        SingleLocalOoiMapper localOoiMapper = pssGroup.getLocalOoiMapper();
        Map<Integer, Ooi> idToOoiMap = localOoiMapper.getIdToOoiMap();
        if (idToOoiMap.containsKey(localOoiId))
            return idToOoiMap.get(localOoiId);
        throw new PssException(String.format("Ooi not found for local id %d for User with id %d", localOoiId, user.getId()));
    }

    @Override
    public Ooi getOoi(int localOoiId, int userId) throws PssException {
        User user = getUser(userId);
        return getOoi(localOoiId, user);
    }
}
