
rootProject.name = "transactional-message-demo"

include(
    ":api",
    ":order-service",
    ":subscribe-service",
    "tailing-service"
)
