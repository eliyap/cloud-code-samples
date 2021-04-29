package cloudcode.guestbook.frontend;

public class StockInfo {

  public StockMeta metadata;

  // only returned for non-authenticated users
  public StockDetails price;

  // only returned for authenticated users
  public StockIEX iex;

  // No Args Constructor for Jackson
  public StockInfo() {}

  public StockInfo(StockDetails price, StockMeta metadata) {
    this.price = price;
    this.metadata = metadata;
  }
  
  public StockInfo(StockIEX iex, StockMeta metadata) {
    this.iex = iex;
    this.metadata = metadata;
  }
}
