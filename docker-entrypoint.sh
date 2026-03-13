#!/bin/bash
set -e

# Start the application in background and capture logs
java -jar /app/shopizer.jar > /tmp/shopizer.log 2>&1 &
APP_PID=$!

# Wait for application to fully start
echo "Waiting for Shopizer to start..."
tail -f /tmp/shopizer.log &
TAIL_PID=$!

for i in {1..240}; do
    if grep -q "Started ShopApplication" /tmp/shopizer.log; then
        echo "Shopizer has fully started!"
        kill $TAIL_PID 2>/dev/null || true
        sleep 5  # Extra wait
        break
    fi
    sleep 1
done

# Add sample products
echo "Adding sample products..."
cd /app
./add-sample-product.sh || true
./add-electronics-products.sh || true
echo "Products added!"

# Keep container running
wait $APP_PID
