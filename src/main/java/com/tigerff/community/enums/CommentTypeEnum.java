package com.tigerff.community.enums;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/27 16:11
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;



    public Integer getType() {
        return type;
    }
    CommentTypeEnum(Integer type)
    {
        this.type=type;
    }
    public static boolean isExist(Integer type) {
        for(CommentTypeEnum typeEnum:CommentTypeEnum.values())
        {
            if(typeEnum.getType()==type)
                return true;
        }
        return false;
    }

}
