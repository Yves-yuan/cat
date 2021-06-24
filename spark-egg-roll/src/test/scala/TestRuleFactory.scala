import validate.rule.RuleFactory

object TestRuleFactory {
  def main(args: Array[String]): Unit = {
    val rule = RuleFactory.getTableRule("D:\\dev\\giospark24\\src\\main\\resources\\ods.ods_mp_wi_product_df.json")
    println(rule)
  }
}
