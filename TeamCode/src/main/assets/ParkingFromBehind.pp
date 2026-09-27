{
  "startPoint": {
    "x": 32,
    "y": 133,
    "name": "Start1",
    "locked": false,
    "headingDeg": 90
  },
  "lines": [
    {
      "id": "line-mud0dvgt-8spkad",
      "color": "#C7CBD6",
      "name": "Path 8",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 500,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 32,
        "y": 120,
        "locked": false
      },
      "controlPoints": [],
      "heading": {
        "type": "constant",
        "reverse": false,
        "degrees": 270
      }
    },
    {
      "id": "line-mud0ojqp-vatfp7",
      "color": "#CD6D5B",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 8,
        "y": 120
      },
      "controlPoints": [],
      "heading": {
        "type": "constant",
        "reverse": true,
        "piecewiseHeading": {
          "segments": [
            {
              "startProgress": 0,
              "endProgress": 1,
              "interpolationType": "linear",
              "reversed": false,
              "parameters": {
                "startDeg": 0,
                "endDeg": 0
              }
            }
          ]
        },
        "startDeg": 0,
        "endDeg": 0,
        "degrees": 270
      }
    }
  ],
  "shapes": [
    {
      "id": "triangle-1",
      "name": "Red Goal",
      "vertices": [
        {
          "x": 141.5,
          "y": 70
        },
        {
          "x": 141.5,
          "y": 141.5
        },
        {
          "x": 118.3,
          "y": 141.5
        },
        {
          "x": 135.5,
          "y": 118
        },
        {
          "x": 136.3,
          "y": 70.2
        }
      ],
      "color": "#dc2626",
      "fillColor": "#ff6b6b"
    },
    {
      "id": "triangle-2",
      "name": "Blue Goal",
      "vertices": [
        {
          "x": 6.2,
          "y": 116.9
        },
        {
          "x": 25,
          "y": 141.5
        },
        {
          "x": 0,
          "y": 141.5
        },
        {
          "x": 0,
          "y": 70
        },
        {
          "x": 6,
          "y": 70
        }
      ],
      "color": "#2563eb",
      "fillColor": "#60a5fa"
    }
  ],
  "sequence": [
    {
      "kind": "path",
      "lineId": "line-mud0dvgt-8spkad"
    },
    {
      "kind": "path",
      "lineId": "line-mud0ojqp-vatfp7"
    }
  ],
  "fieldPoints": [],
  "version": "1.5.0",
  "timestamp": "2026-09-22T18:41:06.838Z"
}