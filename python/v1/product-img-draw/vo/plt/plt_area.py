from marshmallow import Schema, fields, EXCLUDE
import marshmallow_dataclass

@marshmallow_dataclass.dataclass
class PltArea:
    title: str
    font_size: int = 9

    class Meta:
        unknown = EXCLUDE