from service.draw_service import DrawService
from injector import inject

class DrawServiceFactory:

    @inject
    def __init__(self, services: list[DrawService]):
        self.services = services

    '''
    获取实例对象
    '''
    def find_service(self, draw_type: str):
        for service in self.services:
            if service.is_accept(draw_type=draw_type):
                return service
