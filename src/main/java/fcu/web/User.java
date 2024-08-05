package fcu.web;

public class User {
  private String userName;
  private boolean active;
  private boolean feature1;
  private boolean feature2;
  private boolean feature3;

  public User(String userName, boolean active, boolean feature1, boolean feature2, boolean feature3) {
    this.userName = userName;
    this.active = active;
    this.feature1 = feature1;
    this.feature2 = feature2;
    this.feature3 = feature3;
  }

  public String getUserName() {
    return userName;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
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
