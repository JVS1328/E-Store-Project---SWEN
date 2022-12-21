export class Product {
	constructor (
		public id: number = 0,
		public name: string = "Untitled.txt",
		public description: string = "No Description",
		public price: number = 0,
		public quantity: number = 0,
		public picture: string = "/asserts/car.png"
	) {}
}