package com.huateng.sumer.sandbox.web.db.model;

import java.io.Serializable;

public class TblAdmGroupAuthorities implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column tbl_adm_group_authorities.group_id
     *
     * @ibatorgenerated
     */
    private Integer groupId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column tbl_adm_group_authorities.authority_id
     *
     * @ibatorgenerated
     */
    private String authorityId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column tbl_adm_group_authorities.group_id
     *
     * @return the value of tbl_adm_group_authorities.group_id
     *
     * @ibatorgenerated
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column tbl_adm_group_authorities.group_id
     *
     * @param groupId the value for tbl_adm_group_authorities.group_id
     *
     * @ibatorgenerated
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column tbl_adm_group_authorities.authority_id
     *
     * @return the value of tbl_adm_group_authorities.authority_id
     *
     * @ibatorgenerated
     */
    public String getAuthorityId() {
        return authorityId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column tbl_adm_group_authorities.authority_id
     *
     * @param authorityId the value for tbl_adm_group_authorities.authority_id
     *
     * @ibatorgenerated
     */
    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId == null ? null : authorityId.trim();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof TblAdmGroupAuthorities)) {
            return false;
        }
        TblAdmGroupAuthorities other = (TblAdmGroupAuthorities) that;
        return this.getGroupId() == null ? other == null : this.getGroupId().equals(other.getGroupId())
            && this.getAuthorityId() == null ? other == null : this.getAuthorityId().equals(other.getAuthorityId());
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table tbl_adm_group_authorities
     *
     * @ibatorgenerated
     */
    @Override
    public int hashCode() {
        int hash = 23;
        if (getGroupId() != null) {
            hash *= getGroupId().hashCode();
        }
        if (getAuthorityId() != null) {
            hash *= getAuthorityId().hashCode();
        }
        return hash;
    }
}