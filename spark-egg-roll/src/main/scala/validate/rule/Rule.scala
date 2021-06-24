package validate.rule

import org.apache.spark.sql.Row
import validate.rule.result.Result

/**
 *
 * CREATE TABLE ods.ods_data_validate_result
 * (
 * `table_name` String,
 * `validate_type` Enum8('completeness' = 1, 'uniqueness' = 2, 'row_number' = 3,'rule' = 4),
 * `validate_target` String,
 * `validate_result` String,
 * `dt` String
 * )
 * ENGINE = ReplacingMergeTree
 * PRIMARY KEY (table_name, validate_type, validate_target, dt)
 * ORDER BY (table_name, validate_type, validate_target, dt)
 * SETTINGS index_granularity = 8192 │
 *
 */
trait Rule extends Serializable {
  def validate(data: Row): Seq[Result]
}
