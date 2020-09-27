import { CompanyTag } from "./CompanyTag";
import { TopicTag } from "./TopicTag";

export interface PeriodicElement {
    title: string;
    difficultyLevel: String;
    questionRating: String;
    companyTags: CompanyTag[];
    url;
    topicTags: TopicTag[];
  }