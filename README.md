# Social Media Platform - Backend

This is the backend of a social media platform built with **Spring Boot**. It handles user authentication, post management, media upload, and more.

## 🚀 Features

- User registration & login (with JWT authentication)
- Create/update/delete posts
- Upload photos and videos (Cloudflare R2)
- Redis blacklist support for token invalidation
- Email validation
- Secure HTTPS with SSL encryption
- Instant messaging via WebSocket
- Survey system support

## 🛠️ Tech Stack (Backend)

- Java 17 / Spring Boot
- MySQL
- Redis
- Cloudflare R2 (object storage)
- Spring Security
- JPA & Hibernate
- Web Socket

> ⚠️ This repository contains only the backend side of the project.  
> The following technologies are used in other components (not included here):  
> - React (Frontend - not in this repo)  
> - Nginx (for reverse proxy)  
> - FastAPI (planned for survey discovery microservice)  
> - Falcon AI (used for image generation)
