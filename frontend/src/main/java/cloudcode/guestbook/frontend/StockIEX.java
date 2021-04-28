package cloudcode.guestbook.frontend;

public class StockIEX {

  public String ticker;
  public Float last;
  public Float low;
  public Float high;
  public Float open;
  public Float mid;
  public Float bidSize;
  public Float bidPrice;
  public Float askSize;
  public Float askPrice;
  public Integer volume;

  // No Args Constructor for Jackson
  public StockIEX() {}
}
