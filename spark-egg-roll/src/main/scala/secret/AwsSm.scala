package secret

import io.growing.boxer.kms.{AwsSecretProvider, SecretsManagerClientBuilder}

import java.util.Properties

object AwsSm {
  def getSecret(key: String, region: String): String = {
    val properties: Properties = new Properties()
    properties.put("region", region)
    val client = SecretsManagerClientBuilder.build(properties)
    val secretProvider = new AwsSecretProvider(client)
    val secret = secretProvider.getSecret(key)
    secret
  }
}
