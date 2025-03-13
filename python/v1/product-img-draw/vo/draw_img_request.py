
from marshmallow import Schema, fields, EXCLUDE
from vo.plt.plt_area import PltArea
import marshmallow_dataclass

@marshmallow_dataclass.dataclass
class DrawImgRequest:
    type: str
    shp_path: str
    plt_area: PltArea

    class Meta:
        unknown = EXCLUDE