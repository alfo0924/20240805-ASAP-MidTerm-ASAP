package fcu.web;

public class User {
  private String userName;
  private boolean isActive;
  private boolean feature1;
  private boolean feature2;
  private boolean feature3;

  public User(String userName, boolean isActive, boolean feature1, boolean feature2, boolean feature3) {
    this.userName = userName;
    this.isActive = isActive;
    this.feature1 = feature1;
    this.feature2 = feature2;
    this.feature3 = feature3;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public boolean isFeature1() {
    return feature1;
  }

  public void setFeature1(boolean feature1) {
    this.feature1 = feature1;
  }

  public boolean isFeature2() {
    return feature2;
  }

  public void setFeature2(boolean feature2) {
    this.feature2 = feature2;
  }

  public boolean isFeature3() {
    return feature3;
  }

  public void setFeature3(boolean feature3) {
    this.feature3 = feature3;
  }
}
