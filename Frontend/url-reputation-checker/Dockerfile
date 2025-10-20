# Stage 1: Build Stage
# This stage builds the React application
FROM node:18-alpine AS build

# Set working directory
WORKDIR /app

# Copy package files
COPY package.json package-lock.json ./

# Install dependencies
# --frozen-lockfile ensures exact versions from lock file
RUN npm ci --frozen-lockfile

# Copy source code
COPY . .

# Build the React app for production
# This creates an optimized build in the 'build' folder
RUN npm run build

# Stage 2: Production Stage
# Use nginx to serve the static files
FROM nginx:alpine

# Copy custom nginx configuration (we'll create this)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built files from build stage to nginx html directory
COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80 (nginx default)
EXPOSE 80

# Nginx runs in foreground by default
CMD ["nginx", "-g", "daemon off;"]
