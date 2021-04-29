package cloudcode.guestbook.frontend;

import java.util.Date;

public class StockIEX {

  public String ticker;
  public Float last;
  public Float prevClose;
  public Float low;
  public Float high;
  public Float open;
  public Float mid;
  public Float bidSize;
  public Float bidPrice;
  public Float askSize;
  public Float askPrice;
  public Integer volume;
  public Date timestamp;

  // shimmed variable indicating whether the user has this on their favorites list
  public boolean isFavorite;

  // No Args Constructor for Jackson
  public StockIEX() {}

  public String changeStr;
  public Float change;
  public Float percentChange;
  public String color;
  public String arrow;

  public void calculate() {
    change = (float) Math.round((last - prevClose) * 100) / (float) 100;
    percentChange =
      (float) Math.round(change / prevClose * 100 * 100) / (float) 100;
    changeStr = change + " (" + percentChange + ")%";
    color = change > 0 ? "green" : "red";
    arrow = "fas fa-caret-" + (change > 0 ? "up" : "down");
  }
}
