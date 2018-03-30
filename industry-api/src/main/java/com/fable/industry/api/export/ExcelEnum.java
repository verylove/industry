package com.fable.industry.api.export;

/**
 * @author jiangmingjun
 * @create 2018/3/1
 * @description 自定义枚举类 通过中文名称获得英文名称
 */
public enum ExcelEnum {
    /**
     * 目录枚举
     */
    Catalog01("目录名称","catalogName"),
    Catalog02("目录审核单位","comName"),
    Catalog03("目录审核员","userName"),
    Catalog04("联系电话","telephone"),

    /**
     * 数据元枚举
     */
    Element01("数据元名称","dataName"),
    Element02("数据元英文名称","dataEname"),
    Element03("数据元缩写名","dataAname"),
    Element04("数据元定义","dataDefinition"),
    Element06("数据元分类","itemName"),
    Element08("最大出现次数","frequencyMax"),
    Element09("约束条件","constraint"),
    Element10("版本号","versionName"),
    Element11("备注","note");

    private String name;
    private String eName;

    ExcelEnum(String name, String eName) {
        this.name = name;
        this.eName = eName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public static String getEname(String name) {
        for (ExcelEnum d : ExcelEnum.values()
             ) {
            if (d.getName().equals(name)) {
                return d.eName;
            }
        }
        return name;
    }
}
