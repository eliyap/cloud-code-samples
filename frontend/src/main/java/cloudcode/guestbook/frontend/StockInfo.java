package cloudcode.guestbook.frontend;

public class StockInfo {

  public StockDetails price;
  public StockMeta metadata;

  // No Args Constructor for Jackson
  public StockInfo() {}

  public StockInfo(StockDetails price, StockMeta metadata) {
    this.price = price;
    this.metadata = metadata;
  }
}
