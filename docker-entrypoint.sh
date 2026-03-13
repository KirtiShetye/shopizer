#!/bin/bash
set -e

# Start the application in background
java -jar /app/shopizer.jar &
APP_PID=$!

# Wait for application to be ready
echo "Waiting for Shopizer to start..."
for i in {1..120}; do
    if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo "Shopizer is ready!"
        sleep 10  # Extra wait for full initialization
        break
    fi
    sleep 3
done

# Add sample products
echo "Adding sample products..."
cd /app
./add-sample-product.sh || true
./add-electronics-products.sh || true
echo "Products added!"

# Keep container running
wait $APP_PID
