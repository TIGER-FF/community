package com.tigerff.community.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.count_id
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    private String countId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.name
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.token
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gmt_create
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gmt_update
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    private Long gmtUpdate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.avatar_url
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    private String avatarUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.count_id
     *
     * @return the value of user.count_id
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public String getCountId() {
        return countId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.count_id
     *
     * @param countId the value for user.count_id
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public void setCountId(String countId) {
        this.countId = countId == null ? null : countId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.name
     *
     * @return the value of user.name
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.name
     *
     * @param name the value for user.name
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.token
     *
     * @return the value of user.token
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.token
     *
     * @param token the value for user.token
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gmt_create
     *
     * @return the value of user.gmt_create
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gmt_create
     *
     * @param gmtCreate the value for user.gmt_create
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gmt_update
     *
     * @return the value of user.gmt_update
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public Long getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gmt_update
     *
     * @param gmtUpdate the value for user.gmt_update
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public void setGmtUpdate(Long gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.avatar_url
     *
     * @return the value of user.avatar_url
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.avatar_url
     *
     * @param avatarUrl the value for user.avatar_url
     *
     * @mbg.generated Fri Mar 05 21:48:38 CST 2021
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }
}