package com.lsm1998.util.chart;

/**
 * @作者：刘时明
 * @时间:2019/1/1-21:29
 * @说明：顶点对象
 */
public class City
{
    private String cityName;
    private int population;
    private String mayor;

    public City(String cityName, int population, String mayor)
    {
        this.cityName = cityName;
        this.population = population;
        this.mayor = mayor;
    }

    public String getCityName()
    {
        return cityName;
    }

    public int getPopulation()
    {
        return population;
    }

    public String getMayor()
    {
        return mayor;
    }
}
