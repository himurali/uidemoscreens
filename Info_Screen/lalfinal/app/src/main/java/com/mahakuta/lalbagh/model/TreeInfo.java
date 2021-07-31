package com.mahakuta.lalbagh.model;

import java.io.Serializable;

public class TreeInfo  implements Serializable  {


    String title;
    String description;

    Double lattitude;
    Double longitude;

    String whereItGrows;
    String leaves;
    String flowers;
    String fruits;
    String uses;
    String stories;
    String floweringSeasons;

    String leavesUrl;
    String flowersUrl;
    String fruitsUrl;
    String treeCompleteUrl;

    String topimage;

    public TreeInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getWhereItGrows() {
        return whereItGrows;
    }

    public void setWhereItGrows(String whereItGrows) {
        this.whereItGrows = whereItGrows;
    }

    public String getLeaves() {
        return leaves;
    }

    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public String getFruits() {
        return fruits;
    }

    public void setFruits(String fruits) {
        this.fruits = fruits;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getStories() {
        return stories;
    }

    public void setStories(String stories) {
        this.stories = stories;
    }

    public String getFloweringSeasons() {
        return floweringSeasons;
    }

    public void setFloweringSeasons(String floweringSeasons) {
        this.floweringSeasons = floweringSeasons;
    }

    public String getLeavesUrl() {
        return leavesUrl;
    }

    public void setLeavesUrl(String leavesUrl) {
        this.leavesUrl = leavesUrl;
    }

    public String getFlowersUrl() {
        return flowersUrl;
    }

    public void setFlowersUrl(String flowersUrl) {
        this.flowersUrl = flowersUrl;
    }

    public String getFruitsUrl() {
        return fruitsUrl;
    }

    public void setFruitsUrl(String fruitsUrl) {
        this.fruitsUrl = fruitsUrl;
    }

    public String getTreeCompleteUrl() {
        return treeCompleteUrl;
    }

    public void setTreeCompleteUrl(String treeCompleteUrl) {
        this.treeCompleteUrl = treeCompleteUrl;
    }

    public String getTopimage() {
        return topimage;
    }


    public void setTopimage(String topimage) {
        this.topimage = topimage;
    }

}
